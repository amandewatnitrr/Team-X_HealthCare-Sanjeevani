import Adafruit_BMP.BMP085 as BMP085
import time
import pyrebase
import requests
import random
import decimal
import math
import board
import csv
sensor = BMP085.BMP085()
from firebase import firebase
from firebase.firebase import FirebaseAuthentication

TOKEN = "AIzaSyCiU34fRzMwA2ZmUJIkzct4XCR-7wQ9jiw"  # Put your TOKEN here
DSN = 'https://pocket-clinic-8d011.firebaseio.com/' # 'https://myapp.firebaseio.com/'
#SECRET = 'shruti7376036196' # 'secretkey'
#EMAIL ='shruti456rawal@gmail.com' # 'prateekrai266@gmail.com'
#authentication = FirebaseAuthentication(SECRET,EMAIL, True, True)
success = 0

config = {
  "apiKey": "database-secret",
  "authDomain": "pocket-clinic-8d011.firebaseapp.com",
  "databaseURL": "https://pocket-clinic-8d011.firebaseio.com/",
  "storageBucket": "pocket-clinic-8d011.appspot.com"
}

firebase = pyrebase.initialize_app(config)
db = firebase.database()

def update_database():
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
        from firebase import firebase
        firebase = firebase.FirebaseApplication('https://pocket-clinic-8d011.firebaseio.com', None)
        result = firebase.get('/iot','')
    data = {"Temperature": sensor.read_temperature(), "Blood Pressure": sensor.read_pressure(), "Pulse rate": oxi_level}                       
    db.child("iot").child("0001").set(data)
    time.sleep(1)
    
while True:
    update_database()
