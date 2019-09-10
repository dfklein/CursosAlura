import RPi.GPIO as GPIO
import time

## Este scrpit foi feito para acender e apagar um led ligado no GPIO 7 a cada 2 segundos.
## Veja os arquivos esquema circuito.png e circuito-montado.png

GPIO.setmode(GPIO.BOARD) 	# diz que utilizará o GPIO pelo número do pino (Physical).
# GPIO.setmode(GPIO.BCM)    # diz que utilizará o GPIO pelo BCM
GPIO.setwarnings(False)		# desabilita warnings.

GPIO.setup(7,GPIO.OUT)

while(1):
    print('LED ON')
    GPIO.output(7,GPIO.HIGH)

    time.sleep(2)

    print('LED OFF')
    GPIO.output(7,GPIO.LOW)
    time.sleep(2)