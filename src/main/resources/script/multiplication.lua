---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by zhangyao.
--- DateTime: 2023/7/3 10:37
--- 整型乘法

local curVal = redis.call("get", KEYS[1]);
if curVal == false
then
    curVal = 0
else
    curVal = tonumber(curVal)
end ;
curVal = curVal * tonumber(ARGV[1]);
redis.call("set", KEYS[1], curVal);
return curVal;
