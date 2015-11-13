-- main.lua --
----------------------------------
-- WiFi Connection Verification --
----------------------------------
wifi.sta.connect()
tmr.alarm(0, 1000, 1, function()
   if wifi.sta.getip() == nil then
      print("Connecting to AP...\n")
   else
      ip, nm, gw=wifi.sta.getip()
      print("IP Info: \nIP Address: ",ip)
      print("Netmask: ",nm)
      print("Gateway Addr: ",gw,'\n')
      tmr.stop(0)
   end
end)

require('ds18b20')
--GPIO 5 = 1
--GPIO 4 = 2
--GPIO 2 = 4
--GPIO 0 = 3
led_pin = 1
cal_pin = 2
ir_pin = 0
tmp_pin = 4
prz_pin = 6

print("Setting Up GPIO...")
gpio.mode(led_pin, gpio.OUTPUT)
gpio.mode(ir_pin, gpio.INPUT)
gpio.mode(cal_pin, gpio.OUTPUT)
gpio.mode(tmp_pin, gpio.INPUT)
gpio.mode(prz_pin, gpio.OUTPUT)


file.open("State.lua", "r")
StBec = file.read(2)
file.close()

file.open("Stcal.lua", "r")
StCal = file.read(2)
file.close()

file.open("Stprz.lua", "r")
StPrz = file.read(2)
file.close()

if (StCal == "OF") then
	gpio.write(cal_pin,gpio.LOW)
else
	gpio.write(cal_pin,gpio.HIGH)
end

if (StBec == "OF") then
	gpio.write(led_pin,gpio.LOW)
else
	gpio.write(led_pin,gpio.HIGH)
end

if (StPrz == "OF") then
	gpio.write(prz_pin,gpio.LOW)
else
	gpio.write(prz_pin,gpio.HIGH)
end

MAC = wifi.sta.getmac()

print("Starting Web Server...")
-- Create a server object with 30 second timeout
srv = net.createServer(net.TCP, 30)
srv:listen(80,function(conn)
	conn:on("receive", function(conn, payload)
		function esp_update()
            action=string.sub(payload,postparse[2]+1,#payload)
			
			 if action == "ADC" then 
				U = adc.read(0)
				conn:send('HTTP/1.1 200 Content-Type:application/json;charset=utf-8 \n\n')
				conn:send('{"data": "'..U..'","MAC": "'..MAC..'"}')
			end
  
			
			if action == "STCAL" then 
			conn:send('HTTP/1.1 200 Content-Type:application/json;charset=utf-8 \n\n')
				if (StCal == "OF") then
					conn:send('{"data": "OFF","MAC": "'..MAC..'"}')
				else
					conn:send('{"data": "ON","MAC": "'..MAC..'"}')
				end
			end
					
			if (action == "CAL") then
				if (StCal == "OF") then
					gpio.write(cal_pin,gpio.HIGH)
					StCal = "ON"
					conn:send('HTTP/1.1 200 Content-Type:application/json;charset=utf-8 \n\n')
					conn:send('{"data": "ON","MAC": "'..MAC..'"}')
				else
					gpio.write(cal_pin,gpio.LOW)
					StCal = "OF"
					conn:send('HTTP/1.1 200 Content-Type:application/json;charset=utf-8 \n\n')
					conn:send('{"data": "OFF","MAC": "'..MAC..'"}')
				end
				file.open("Stcal.lua", "w+")
				file.writeline(StCal)
				file.close()
			end
			
			if action == "STPRZ" then 
			conn:send('HTTP/1.1 200 Content-Type:application/json;charset=utf-8 \n\n')
				if (StPrz == "OF") then
					conn:send('{"data": "OFF","MAC": "'..MAC..'"}')
				else
					conn:send('{"data": "ON","MAC": "'..MAC..'"}')
				end
			end
					
			if (action == "PRZ") then
				if (StPrz == "OF") then
					gpio.write(prz_pin,gpio.HIGH)
					StPrz = "ON"
					conn:send('HTTP/1.1 200 Content-Type:application/json;charset=utf-8 \n\n')
					conn:send('{"data": "ON","MAC": "'..MAC..'"}')
				else
					gpio.write(prz_pin,gpio.LOW)
					StPrz = "OF"
					conn:send('HTTP/1.1 200 Content-Type:application/json;charset=utf-8 \n\n')
					conn:send('{"data": "OFF","MAC": "'..MAC..'"}')
				end
				file.open("Stprz.lua", "w+")
				file.writeline(StPrz)
				file.close()
			end
  
			if action == "TMP" then 
				-- ESP-01 GPIO Mapping
				gpio5 =4
				ds18b20.setup(gpio5)
				t=ds18b20.read()
				if(t==nil) then
				t=0
				end
				conn:send('HTTP/1.1 200 Content-Type:application/json;charset=utf-8 \n\n')
				conn:send('{"data": "'..t..'","MAC": "'..MAC..'"}')
			end
			
			if action == "ST" then 
			conn:send('HTTP/1.1 200 Content-Type:application/json;charset=utf-8 \n\n')
				if (StBec == "OF") then
					conn:send('{"data": "OFF","MAC": "'..MAC..'"}')
				else
					conn:send('{"data": "ON","MAC": "'..MAC..'"}')
				end
			end
			
			if action == "STIR" then 
			conn:send('HTTP/1.1 200 Content-Type:application/json;charset=utf-8 \n\n')
				gpio.write(ir_pin,gpio.LOW)
            	if gpio.read(ir_pin) == 0 then
					conn:send('{"data": "OFF","MAC": "'..MAC..'"}')
				else
					conn:send('{"data": "ON","MAC": "'..MAC..'"}')
				end	 
			end
					
		if (action == "BEC") then
				if (StBec == "OF") then
					gpio.write(led_pin,gpio.HIGH)
					StBec = "ON"
					U = adc.read(0)
					print("ADC:" .. U .. " C\n")
					conn:send('HTTP/1.1 200 Content-Type:application/json;charset=utf-8 \n\n')
					conn:send('{"data": "'..U..'","MAC": "'..MAC..'"}')
				else
					gpio.write(led_pin,gpio.LOW)
					StBec = "OF"
					U = adc.read(0)
					print("ADC:" .. U .. " C\n")
					conn:send('HTTP/1.1 200 Content-Type:application/json;charset=utf-8 \n\n')
					conn:send('{"data": "'..U..'","MAC": "'..MAC..'"}')
				end
				file.open("State.lua", "w+")
				file.writeline(StBec)
				file.close()
			end
			
        end
        postparse={string.find(payload,"action=")}
        if postparse[2]~=nil then esp_update()end
        conn:on("sent", function(conn) conn:close() end)
	end)
end)