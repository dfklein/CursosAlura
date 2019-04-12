import React, { Component } from 'react';
import {
    View,
    Image,
    TouchableOpacity,
    TextInput,
    StyleSheet
} from 'react-native';

export default class InputComentario extends Component {
    constructor() {
        super();
        this.state = {
            valorComentario: ''
        }
    }
    render() {
        return (
            <View style={styles.container}>
                <TextInput style={styles.input}
                    placeholder="Adicione um comentário..."
                    ref={input => this.inputComentario = input}
                    onChangeText={texto => this.setState({ valorComentario: texto })}
                    underlineColorAndroid="transparent" />
                {/* underlineColorAndroid é uma propriedade para remover uma linha
                         inferior que o componente de input do Android tem por padrão e que
                         não existe no IOS  */}

                {/* o atributo ref cria uma referência para o próprio input */}

                <TouchableOpacity onPress={() => {
                    this.props.comentarioCallback(this.props.idFoto, this.state.valorComentario, this.inputComentario)
                    this.setState({ valorComentario: '' })
                }}>
                    <Image style={styles.icone}
                        source={require('../../resources/img/send.png')} />
                </TouchableOpacity>
            </View>
        );
    }
}

const styles = StyleSheet.create({

    container: {
        flexDirection: 'row',
        alignItems: 'center',
        borderBottomWidth: 1,
        borderBottomColor: '#ddd',
    },

    input: {
        flex: 1,
        height: 40
    },

    icone: {
        width: 30,
        height: 30
    }

});
