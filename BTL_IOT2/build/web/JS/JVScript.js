/* global mqtt, x */
const client = mqtt.connect('ws://broker.mqtt-dashboard.com:8000/mqtt');

client.on('connect', function () {
    console.log('Connected to MQTT broker');
    client.subscribe('/IOT/BTL/state', { qos: 0 });
    client.subscribe('/IOT/BTL/distance', { qos: 1 });
    //client.subscribe('/IOT/BTL/percent', { qos: 2 });
});

var min = document.getElementById("Min").value;
var max = document.getElementById("Max").value;
var height = document.getElementById("Height").value;

client.on('message', (topic, message, packet) => {
    //console.log(`Received Message: ${message.toString()} On topic: ${topic}`);
    if (topic === '/IOT/BTL/state'){
        var newState = message.toString();
        document.getElementById('state').innerHTML = newState;
    }

    if (topic === '/IOT/BTL/distance'){
        var distance = message.toString();
        document.getElementById('distance').innerHTML = distance;
        var temp = Percent(height, message);
        updateCircularGauge(temp);
        var state = document.getElementById("state").innerText;
        if (temp >= max && state!=='Off') client.publish('/IOT/BTL/pump', '0');
        if (temp <= min && state!=='On') client.publish('/IOT/BTL/pump', '1');
    }

});

const circularProgress = document.getElementById('progress');
const circularPercentageText = document.getElementById('percentage');

// Hàm cập nhật gauge với giá trị phần trăm mới
function updateCircularGauge(percentage) {
  const validPercentage = Math.min(100, Math.max(0, percentage));
  circularProgress.style.transform = `scaleY(${validPercentage / 100})`;
  // Chuyển đổi màu từ đỏ đến xanh
  const hue = (validPercentage * 1.1);
  circularProgress.style.backgroundColor = `hsl(${hue}, 100%, 50%)`;
  circularPercentageText.textContent = `${validPercentage}%`;
}

function buttonPressed(action) {
    //Publish command to turn on or off pump
    client.publish('/IOT/BTL/pump', action);
    //console.log(`Sent message to turn ${action === '1' ? 'On' : 'Off'} pump`);
};

function Percent(height, distance) {
    return (height-distance)*100/height;
}
        
var stateElement = document.getElementById("state");
var distanceElement = document.getElementById("distance");
var distanceInputElement = document.getElementById("distanceInput");

// Biến để theo dõi giá trị trước đó của 'state'
var prevState = stateElement.innerText;

// Tạo một MutationObserver để theo dõi sự thay đổi của nội dung trong span 'state'
var observer = new MutationObserver(function(mutationsList) {
    for (var mutation of mutationsList) {
        if (mutation.type === 'childList' || mutation.type === 'subtree') {
            // Lấy giá trị mới từ span 'state'
            var currentState = stateElement.innerText;
            if (currentState === '-') break;
            // Kiểm tra nếu giá trị đã thay đổi
            if (currentState !== prevState) {
                // Lấy giá trị từ span 'distance'
                var distanceValue = distanceElement.innerText;
                // Gán giá trị vào input hidden
                distanceInputElement.value = distanceValue;
                // Hiển thị thông báo
                console.log("Giá trị của distanceInput được cập nhật thành: " + distanceValue);
                sendToServlet(distanceValue);
                // Cập nhật giá trị trước đó của 'state'
                prevState = currentState;
                        
            }
        }
    }
});

// Đặt cấu hình cho MutationObserver
var config = { childList: true, subtree: true };
// Bắt đầu quan sát sự thay đổi trong span 'state'
observer.observe(stateElement, config);

function sendToServlet(value) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "IOT", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("distance=" + encodeURIComponent(value));
};





