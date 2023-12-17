Create database IOT2;
use IOT2;

create table WaterPump (
	[Ngay] datetime null,
	[Water] float null,
)

-- drop table WaterPump

create table Parameter (
	[Id] int,
	[Min] int,
	[Max] int,
	[Height] int,
	[Radius] int,
)
-- drop table Parameter

Select * from WaterPump
delete from WaterPump
/* 
select Ngay,Water from WaterPump

Insert WaterPump (Ngay, Water) values ('11-11-2023',124.1);
Delete from WaterPump where water = 4710
insert into Parameter values(1,10,70,20,6)
update Parameter set [Min]=10, [Max]=70, [Height]=20, Radius=5 where [Id]=1;
select * from Parameter 
*/


