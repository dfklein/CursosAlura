import React, { Component } from 'react';
import {
    StyleSheet,
    Text,
    View,
    Image,
    Dimensions,
    TouchableOpacity
} from 'react-native';

const width = Dimensions.get('screen').width;


type Props = {};
export default class Post extends Component<Props> {
    constructor(props) {
        super(props);
        this.state = {
            foto: {
                // O ... é chamado spread operator. Aqui você está falando que a variável
                // "foto" possui todas os atributos de "this.props.foto" e também o
                // atributo "likers" (que depois foi comentado, o que quer dizer que não era
                // mais necessário, mas deixei aqui para não perder esta explicação)
                ... this.props.foto,
                // Foi declarado um objeto vazio para likers apenas para fazer com que sempre
                // seja visualizada uma primeira curtida
                // likers: [{}]
            }
        }
    }

    carregaIcone(likeada) {
        return likeada
            ? require('../../resources/img/s2-checked.png')
            : require('../../resources/img/s2.png');
    }

    like() {
        const { foto } = this.state;
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

        this.setState({ foto: fotoAtualizada });
    }

    exibeLikes(likers) {
        if (likers.length <= 0)
            return;

        return (<Text style={styles.likes}>{likers.length} {likers.length > 1 ? 'curtidas' : 'curtida'}</Text>)
    }

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
            comentarios.map(comentario =>
                <View style={styles.comentario} key={comentario.id}>
                    <Text style={styles.tituloComentario}>{comentario.login}</Text>
                    <Text>{comentario.texto}</Text>
                </View>
            )
        );
    }

    render() {
        const { foto } = this.state;

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
                    <TouchableOpacity onPress={(this.like.bind(this))}>
                        <Image source={this.carregaIcone(foto.likeada)}
                            style={styles.botaoDeLike} />
                    </TouchableOpacity>
                    {this.exibeLikes(foto.likers)}
                    {this.exibeLegenda(foto)}
                    {this.exibeComentarios(foto.comentarios)}
                </View>
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
    botaoDeLike: {
        marginBottom: 10,
        width: 40,
        height: 40
    },

    rodape: {
        margin: 10
    },
    likes: {
        fontWeight: 'bold'
    },
    comentario: {
        flexDirection: 'row'
    },
    tituloComentario: {
        fontWeight: 'bold',
        marginRight: 5
    }
});