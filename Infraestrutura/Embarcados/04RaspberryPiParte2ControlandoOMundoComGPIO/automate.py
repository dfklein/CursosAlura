import RPi.GPIO as GPIO
import sys

# Chamar este codigo como "python automate 7 1", por exemplo

def inicializaBoard():
    GPIO.setmode(GPIO.BOARD)
    GPIO.setwarnings(False)

def definePinoComoSaida(numeroPino):
    GPIO.setup(numeroPino, GPIO.OUT)

def escreveParaPorta(numeroPino, estadoPorta):
    GPIO.output(numeroPino, estadoPorta)

numeroPino = int(sys.argv[1])     # isto aqui e um cast para int porque por default o tipo dos argumentos recebidos em linha de comando sao string.
estadoPorta = int(sys.argv[2])

inicializaBoard()
definePinoComoSaida(numeroPino)
escreveParaPorta(numeroPino, estadoPorta)
