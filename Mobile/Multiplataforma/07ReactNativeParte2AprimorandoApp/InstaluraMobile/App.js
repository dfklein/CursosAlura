import React, { Component } from 'react';
import {
    StyleSheet,
    Dimensions,
    FlatList,
    Platform
} from 'react-native';
import Post from './src/components/Post';


export default class App extends Component {

    constructor() {
        super();
        this.state = {
            fotos: []
        }
    }

    like(idFoto) {

        const foto = this.state.fotos.find(foto => foto.id == idFoto);
        let novaLista = [];

        if (!foto.likeada) {
            novaLista = [
                ...foto.likers,
                { login: 'meuUsuario' }
            ];
        } else {
            novaLista = foto.likers.filter(liker => {
                return liker.login !== 'meuUsuario';
            })
        }
        const fotoAtualizada = {
            ...foto,
            likeada: !foto.likeada,
            likers: novaLista
        }

        const fotos = this.state.fotos
            .map(foto => foto.id === fotoAtualizada.id ? fotoAtualizada : foto);

        this.setState({ fotos: fotos });
        // this.setState({ fotos }); // neste caso esta sintaxe faria a mesma coisa
    }

    adicionaComentario(idFoto, valorComentario, inputComentario) {
        if (valorComentario === '')
            return;

        const foto = this.state.fotos.find(foto => foto.id === idFoto);

        const novaLista = [...foto.comentarios, {
            id: this.state.valorComentario,
            login: 'meuUsuario',
            texto: valorComentario,
        }];

        const fotoAtualizada = {
            ...foto,
            comentarios: novaLista
        }

        const fotos = this.state.fotos.
            map(foto => foto.id === fotoAtualizada.id ? fotoAtualizada : foto)

        // this.setState({ foto: fotoAtualizada });
        this.setState({ fotos });
        inputComentario.clear();
    }

    componentDidMount() {
        // fetch('http://10.0.2.2l:8080/api/public/fotos/rafael')
        fetch('https://instalura-api.herokuapp.com/api/public/fotos/rafael')
            .then(resposta => resposta.json())
            .then(json => this.setState({ fotos: json }))
            .catch(e => {
                console.warn('Não foi possível carregar as fotos: ' + e);
                this.setState({ status: 'ERRO' })
            });
    }


    render() {
        return (

            <FlatList
                // data={fotos}
                data={this.state.fotos}
                keyExtractor={item => item.id.toString()}
                renderItem={({ item }) =>
                    <Post foto={item}
                        likeCallback={this.like.bind(this)}
                        comentarioCallback={this.adicionaComentario.bind(this)} />

                }
            />

        );
    }
}

const margem = Platform.OS == 'ios' ? 20 : 0;

// O StyleSheet é um tipo de CSS do React Native. 
const styles = StyleSheet.create({

    container: {
        marginTop: margem
    },
    cabecalho: {
        margin: 10,
        flexDirection: 'row',
        alignItems: 'center'
    }


});
