

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

    def __pode_sacar(self, valor_a_sacar):
        valor_disponivel_a_sacar = self.__saldo + self.__limite
        return valor_a_sacar <= valor_disponivel_a_sacar

    def saca(self, valor):
        if (self.__pode_sacar(valor)):
            self.__saldo -= valor
        else:
            print("O valor {} passou o limite".format(valor))

    def transfere(self, valor, destino):
        self.saca(valor)
        destino.deposita(valor)

    def get_saldo(self):
        return self.__saldo

    def get_titular(self):
        return self.__titular

    def get_limite(self):
        return self.__limite

    def set_limite(self, limite):
        self.__limite = limite

    @staticmethod
    def codigo_banco():
        return "001"

    @staticmethod
    def codigos_bancos(): # Lembrando que isso aqui é um tipo de Map e que você pode acessar com: codigos['BB']
        return {'BB': '001', 'Caixa': '104', 'Bradesco': '237'}