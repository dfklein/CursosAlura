import React, { Component } from 'react';
import {
    StyleSheet,
    Text,
    View,
    Image,
    Dimensions,
    FlatList
} from 'react-native';
import InputComentario from './InputComentario';
import Likes from './Likes';

const width = Dimensions.get('screen').width;


export default class Post extends Component {


    exibeLegenda(foto) {
        if (foto.comentario == '')
            return;

        return (
            <View style={styles.comentario}>
                <Text style={styles.tituloComentario}>{foto.loginUsuario}</Text>
                <Text>{foto.comentario}</Text>
            </View>
        );
    }


    exibeComentarios(comentarios) {
        return (
            <View>
                <FlatList
                    data={comentarios}
                    keyExtractor={item => item.id}
                    renderItem={({ item }) =>
                        <View style={styles.comentario}>
                            <Text style={styles.tituloComentario}>{item.login}</Text>
                            <Text>{item.texto}</Text>
                        </View>
                    } />
            </View>
        );
    }

    render() {
        const { foto, likeCallback, comentarioCallback } = this.props;

        return (
            <View >
                <View style={styles.cabecalho}>
                    <Image source={{ uri: foto.urlPerfil }}
                        style={styles.fotoDePerfil} />
                    {/* Ao invés de acessar a variável foto como caminho completo ("this.props.foto")
                        você primeiro passou a foto para uma variável do estado da classe. Ela então seria
                        acessada por "this.state.foto". Aí para tornar menos verboso, você extraiu a variável
                        foto do estado da classe para uma variável local chamada apenas de "foto"  */}
                    <Text>{foto.loginUsuario}</Text>
                </View>
                {/* O uri vai basicamente buscar um recurso que tem seu caminho descrito em uma string */}
                <Image source={{ uri: foto.urlFoto }}
                    style={styles.foto} />


                <View style={styles.rodape}>
                    <Likes foto={foto} likeCallback={likeCallback} />

                    {this.exibeLegenda(foto)}
                    {this.exibeComentarios(foto.comentarios)}
                </View>
                <InputComentario 
                    idFoto = {foto.id}
                    comentarioCallback={comentarioCallback} />
            </View>
        );
    }


}

const styles = StyleSheet.create({

    cabecalho: {
        margin: 10,
        flexDirection: 'row',
        alignItems: 'center'
    },
    fotoDePerfil: {
        marginRight: 10,
        borderRadius: 20,
        width: 40, height: 40
    },
    foto: {
        width: width,
        height: width
    },

    rodape: {
        margin: 10
    },
    
    comentario: {
        flexDirection: 'row'
    },
    tituloComentario: {
        fontWeight: 'bold',
        marginRight: 5
    },

});