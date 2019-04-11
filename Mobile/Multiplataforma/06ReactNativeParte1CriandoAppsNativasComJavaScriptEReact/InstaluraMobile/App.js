/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import {
    StyleSheet,
    Dimensions,
    FlatList
} from 'react-native';
import Post from './src/components/Post';

const width = Dimensions.get('screen').width;


type Props = {};
export default class App extends Component<Props> {

    constructor() {
        super();
        this.state = {
            fotos: []
        }
    }

    componentDidMount() {
        fetch('https://instalura-api.herokuapp.com/api/public/fotos/rafael')
            .then(resposta => resposta.json())
            .then(json => this.setState({ fotos: json }))
            .catch(e => {
                console.warn('Não foi possível carregar as fotos: ' + e);
                this.setState({ status: 'ERRO' })
            });
    }


    render() {
        // const fotos = [
        //     { id: '1', usuario: 'rafael' },
        //     { id: '2', usuario: 'alberto' },
        //     { id: '3', usuario: 'vitor' }
        // ];

        return (
            // Deixei o código abaixo comentado apenas como um exemplo de como aplicar o style diretamente dentro da tag.
            // <FlatList style={{ marginTop: 20 }}

            // O FlatList é um dos muitos componentes que te ajudam a trabalhar com iteração
            // sobre coleção para criação de componentes de visualização. Existem vários tipos
            // que você pode consultar em: https://facebook.github.io/react-native/blog/2017/03/13/better-list-views.html
            <FlatList
                // data={fotos}
                data={this.state.fotos}
                keyExtractor={item => item.id.toString()}
                renderItem={({ item }) =>

                    // Assim como no React para web, ao passar uma propriedade através
                    // de uma tag, ela será inserida na variável props do js do componente
                    // que ela chama (ou seja: acessível através de this.props.item) 
                    <Post foto={item} />

                    // ----> O componente abaixo foi comentado por ter sido extraído para um
                    // ----> componente à parte (Post.js)
                    // <View >
                    //     <View style={styles.cabecalho}>
                    //         <Image source={require('./resources/img/s2-checked.png')}
                    //             style={styles.fotoDePerfil} />
                    //         <Text>{item.usuario}</Text>
                    //     </View>
                    //     <Image source={require('./resources/img/s2-checked.png')}
                    //         style={styles.foto} />
                    // </View>
                }
            />

            // ---> O código abaixo foi substituído pelo componente FlatList (na versão em que o Post.js ainda era parte deste)

            // Observe que todas as tags precisam ser importadas. Caso contrário não funciona.
            // <ScrollView style={{marginTop: 20}}>
            //     {fotos.map(foto =>
            //         <View key={foto.id}>
            //             <Text>{foto.usuario}</Text>
            //             {/* <Image source={require = ('./resources/img/s2-checked.png')} /> */}
            //             <Image
            //                 style={{ width: width, height: width }}
            //                 source={{ uri: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADMAAAAzCAYAAAA6oTAqAAAAEXRFWHRTb2Z0d2FyZQBwbmdjcnVzaEB1SfMAAABQSURBVGje7dSxCQBACARB+2/ab8BEeQNhFi6WSYzYLYudDQYGBgYGBgYGBgYGBgYGBgZmcvDqYGBgmhivGQYGBgYGBgYGBgYGBgYGBgbmQw+P/eMrC5UTVAAAAABJRU5ErkJggg==' }}
            //             />
            //         </View>
            //     )}    
            // </ScrollView>
        );
    }
}

// O StyleSheet é um tipo de CSS do React Native. 
const styles = StyleSheet.create({

    container: {
        marginTop: 20
    },
    cabecalho: {
        margin: 10,
        flexDirection: 'row',
        alignItems: 'center'
    }

    // O código abaixo foi gerado automaticamente pelo react. Foi comentado para executar conforme o curso.
    // container: {
    //     flex: 1,
    //     justifyContent: 'center',
    //     alignItems: 'center',
    //     backgroundColor: '#F5FCFF',
    // },
    // welcome: {
    //     fontSize: 20,
    //     textAlign: 'center',
    //     margin: 10,
    // },
    // instructions: {
    //     textAlign: 'center',
    //     color: '#333333',
    //     marginBottom: 5,
    // },

});
