print("*******************************")
print("Bem vindo ao jogo de advinhação")
print("*******************************")

numero_secreto = 42
palpite = input("Digite o seu número: ")

num_palpite = int(palpite)
print("Você digitou", num_palpite)

if (numero_secreto == num_palpite):
    print("Você acertou!")
else:
    print("Você errou!")
print("Fim do jogo")