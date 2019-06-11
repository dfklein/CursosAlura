

class Conta: # Não é o caso aqui, mas para nome de classes o padrão do python é o camel case

    # Este é o construtor da classe. Ele SEMPRE tem esse nome
    # O "self" é o argumento obrigatório para caracterizar o construtor.
    # O "self" é a referência ao objeto que você está construindo. No entanto você NÃO INFORMA ela na
    # instanciação de um novo objeto. Exemplo: conta = Conta(222, "Titular", 12, 1000): não
    # é passado o self
    def __init__(self, numero, titular, saldo, limite):
        print("Construindo objeto...{}".format(self))
        self.__numero = numero  # No python não existe modificador de acesso. Mas uma convenção diz que ao iniciar o
                                # nome de uma variável com "__" significa que essa variável não deve ser acessada
                                # diretamente.
        self.__titular = titular
        self.__saldo = saldo
        self.__limite = limite

    def extrato(self):
        print("Saldo de {} do titular {}".format(self.saldo, self.titular))

    def deposita(self, valor):
        self.saldo += valor

    def saca(self, valor):
        self.saldo -= valor

    def transfere(self, valor, destino):
        self.saca(valor)
        destino.deposita(valor)