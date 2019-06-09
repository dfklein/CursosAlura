import random

# def jogar():

print("*******************************")
print("Bem vindo ao jogo de forca")
print("*******************************")

palavra_secreta = "banana"

enforcou = False
acertou = False

while(not acertou and not enforcou):
    chute = input("Qual letra? ")
    chute = chute.strip() # strip() é a mesma coisa que o trim() do Java

    index = 0
    for letra in palavra_secreta:
        if (chute.upper() == letra.upper()):
            print("Encontrei a letra {} na posição {}".format(letra, index))
        index = index + 1


# if (__name__ == "__main__"):    # Esta é uma verificação para saber se este arquivo foi chamado diretamente ou se
#    jogar()                     # trata-se de algo que está sendo importado por outro arquivo .py
