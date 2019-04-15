import React, { Component } from 'react';
import {
    StyleSheet,
    Text,
    View,
    Dimensions,
    TextInput,
    Button,
    AsyncStorage
} from 'react-native';

const width = Dimensions.get('screen').width;

export default class Login extends Component {

    constructor() {
        super();
        this.state = {
            usuario: '',
            senha: '',
            mensagem: ''
        }
    }

    efetuaLogin() {
        const uri = "http://instalura-api.herokuapp.com/api/public/login";
        const requestInfo = {
            method: 'POST',
            body: JSON.stringify({
                login: this.state.usuario,
                senha: this.state.senha
            }),
            headers: new Headers({
                'Content-type': 'application/json'
            })
        }

        fetch(uri, requestInfo)
            .then(response => {
                if (response.ok)
                    return response.text();

                throw new Error("Não foi possível efetuar o login");

            })
            .then(token => {
                // O AsyncStorage é um objeto que guarda um mapa de atributos
                // no que seria equivalente à sessão http do app
                // AsyncStorage.setItem('token', token);
                // AsyncStorage.setItem('usuario', this.state.usuario);
                AsyncStorage.multiSet([
                    ['usuario', this.state.usuario],
                    ['token', token]
                ]);

                // Para recuperar um objeto: AsyncStorage.getItem('usuario');
                // Para remover um objeto: AsyncStorage.removeItem('usuario');
                // CUIDADO com o AsyncStorage.clear() : A função clear apaga todas as entradas de todos os clientes, apps, libs, etc. 
            })
            .then(token => console.warn('Token: ' + AsyncStorage.getItem('token')))
            .catch(e => this.setState({ mensagem: e.message }))


    }

    // Modelo de logout:
    // logout() {
    //     AsyncStorage.removeItem('usuario');
    //     AsyncStorage.removeItem('token');
    //
    //     this.props.navigator.resetTo({
    //         screen: {
    //             screen: 'Login',
    //             title: 'Login'
    //         }
    //     });
    // }

    render() {
        return (
            <View style={styles.container}>
                <Text style={styles.titulo}>Instalura</Text>
                <View style={styles.form}>

                    <TextInput
                        style={styles.input}
                        placeholder="Usuário..."
                        autoCapitalize="none"
                        onChangeText={texto => this.setState({ usuario: texto })} />

                    <TextInput
                        style={styles.input}
                        placeholder="Senha..."
                        secureTextEntry={true}
                        onChangeText={texto => this.setState({ senha: texto })} />

                    <Button title="Login" onPress={this.efetuaLogin.bind(this)}></Button>


                </View>
                <Text>Usuario: RAFAEL, senha 123456</Text>
                <Text style={styles.mensagem}>
                    {this.state.mensagem}
                </Text>
            </View>
        );

    }

}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },

    form: {
        width: width * 0.8,

    },

    input: {
        height: 40,
        borderBottomWidth: 1,
        borderBottomColor: '#ddd'
    },

    titulo: {
        fontWeight: 'bold',
        fontSize: 26
    },

    mensagem: {
        marginTop: 15,
        color: '#e74c3c'
    }
});