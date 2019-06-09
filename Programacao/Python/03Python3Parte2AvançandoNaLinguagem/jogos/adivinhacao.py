import random

def jogar():

    print("*******************************")
    print("Bem vindo ao jogo de forca")
    print("*******************************")

    print("Qual o nível de dificuldade?")
    print("(1) Fácil (2) Médio (3) Difícil")
    numero_secreto = random.randrange(1, 101)
    pontos = 1000
    nivel = int(input("Defina o nível: "))

    if (nivel == 1):
        total_de_tentativas = 20
    elif (nivel == 2):
        total_de_tentativas = 10
    else:
        total_de_tentativas = 5


    for rodada in range(1, total_de_tentativas + 1):    # você pode definir um terceiro argumento no range que seria o fator de
                                                        # de incremento (por exemplo: se for 2 ele somará de dois em dois).

        print("Tentativa {} de {}".format(rodada, total_de_tentativas))
        palpite = input("Digite o seu número: ")
        chute = int(palpite)
        print("Você digitou", chute)

        if(chute < 1 or chute > 100):
            print("O valor precisa estar entre 1 e 100")
            continue

        acertou = chute == numero_secreto
        maior = chute > numero_secreto
        menor = chute < numero_secreto

        if (acertou):
            print("Você acertou e fez {} pontos!".format(pontos))
            break
        else:
            if (maior):
                print(
                    "Você errou! O seu chute foi maior que o número secreto.")
            elif (menor):
                print(
                    "Você errou! O seu chute foi menor que o número secreto.")
            pontos_perdidos = abs(numero_secreto - chute)
            pontos = pontos - pontos_perdidos


    print("Fim do jogo")

if (__name__ == "__main__"):    # Esta é uma verificação para saber se este arquivo foi chamado diretamente ou se
    jogar()                     # trata-se de algo que está sendo importado por outro arquivo .py
