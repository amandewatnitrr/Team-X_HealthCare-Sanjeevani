# **IoT Scheme | Sanjeevani** :computer:
<p align="center">
  <img width="60%" src="https://github.com/amandewatnitrr/evolution-hacknitr/blob/main/imgs/image_processing20191005-22376-4jawmy.gif">
</p>

## Hardware Requirements :
1. Raspberry Pi
2. Analog to Digital Converter(ADC)
3. Voltage Level Converter
4. Sensors("We used BMP085 and Pulse Oximeter for this purpose.")
5. I2C Bus if using more than 1 I2C based Sensors.

## CAD Model of the MedIoT Kit
The files cannot be shared as they are subjected for confidential research purpose.
<p align="center">
  <img width="40%" src="https://github.com/amandewatnitrr/evolution-hacknitr/blob/main/imgs/isometric_box.png">
  <img width="40%" src="https://github.com/amandewatnitrr/evolution-hacknitr/blob/main/imgs/top_box.png"><br>
  <img width="40%" src="https://github.com/amandewatnitrr/evolution-hacknitr/blob/main/imgs/WhatsApp%20Image%202021-03-20%20at%2013.16.06.jpeg">
</p>

## Our IoT Dashboard and Circuitry
<p align="center">
  <img width="40%" src="https://github.com/amandewatnitrr/evolution-hacknitr/blob/main/imgs/IMG20200820035139.jpg">
  <img width="50%" src="https://github.com/ShrutiRawal/Team-X_HealthCare-Sanjeevani/blob/master/IoT/firebase.PNG">
</p>

## Setting Up the Raspberry Pi
1. Clone this repository on your raspberry pi.
2. Move to the **IoT folder**.
3. Use the below command to install all the required libraries
```pip
pip3 install -r requirements.txt 
```
4. Now as you are ready to work with, in order to make it work, you need to login to your firebase console.
5. In order to make it work, you need to change, the device token, and DSN.

```python3
TOKEN = "AIzaSyCiU34fRzMwA2ZmUJIkzct4XCR-7wQ9jiw"  # Put your TOKEN here
DSN = 'https://pocket-clinic-8d011.firebaseio.com/' # 'https://myapp.firebaseio.com/'
```

6. Change the conifgs.
```python3
config = {
  "apiKey": "database-secret",
  "authDomain": "something.firebaseapp.com",
  "databaseURL": "https://something.firebaseio.com/",
  "storageBucket": "something.appspot.com"
}
```
7. Read the Readings from Sensor.
```python3
 temp = format(sensor.read_temperature())
    pres = format(sensor.read_pressure())
    oxi_level = format(95+float(decimal.Decimal(random.randrange(-85,198))/100))
    if temp is not None and pres is not None and oxi_level is not None:  
        #str_temp = ' {0:0.2f} *C '.temp    
        #str_pres  = ' {0:0.2f} %'.pres
        #str_alti = ' {0:0.2f} m'.alti
        print('Temp = {0:0.2f} *C'.format(sensor.read_temperature()))
        print('Pressure = {0:0.2f} KPa'.format(sensor.read_pressure()/1000))
        print('Oxygen Saturation = {0:0.2f} %'.format(float(oxi_level)))
        print('Sealevel Altitude = {0:0.2f} m'.format(sensor.read_altitude()))
```
8. Publish the data to specific branch with the Firebase Database Tree.
```python3
 data = {"Temperature": sensor.read_temperature(), "Blood Pressure": sensor.read_pressure(), "Pulse rate": oxi_level}                       
    db.child("iot").child("0001").set(data)
```
# Now, you are ready to go ahead :smile:
<p align="center"> 
  <img width="70%" src="https://github.com/amandewatnitrr/evolution-hacknitr/blob/main/imgs/b43db78f64c8e26fb580bb7f00b66222.gif">
</p>
