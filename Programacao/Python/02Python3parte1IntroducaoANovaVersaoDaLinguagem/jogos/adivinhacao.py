print("*******************************")
print("Bem vindo ao jogo de advinhação")
print("*******************************")

numero_secreto = 42
total_de_tentativas = 3


for rodada in range(1, total_de_tentativas + 1):    # você pode definir um terceiro argumento no range que seria o fator de
                                                    # de incremento (por exemplo: se for 2 ele somará de dois em dois).

    print("Tentativa {} de {}".format(rodada, total_de_tentativas))
    palpite = input("Digite o seu número: ")
    print("Você digitou", chute)
    chute = int(palpite)

    if(chute < 1 or chute > 100):
        print("O valor precisa estar entre 1 e 100")
        continue

    acertou = chute == numero_secreto
    maior = chute > numero_secreto
    menor = chute < numero_secreto

    if (acertou):
        print("Você acertou!")
        break
    else:
        if (maior):
            print("Você errou! O seu chute foi maior que o número secreto.")
        elif (menor):
            print("Você errou! O seu chute foi menor que o número secreto.")



print("Fim do jogo")