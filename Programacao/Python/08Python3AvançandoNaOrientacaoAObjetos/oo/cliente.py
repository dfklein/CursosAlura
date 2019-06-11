class Cliente:

    def __init__(self, nome):
        self.__nome = nome

    @property   # O @property torna o getter um tipo de propriedade. Por exemplo:
                #   cliente = Cliente("Fulano")
                #   print(cliente.nome) -> imprime o valor de nome passando pelo getter (ou seja: com upper)
    def nome(self):
        return self.__nome.upper()

    @nome.setter
    def nome(self, nome):
        self.__nome = nome