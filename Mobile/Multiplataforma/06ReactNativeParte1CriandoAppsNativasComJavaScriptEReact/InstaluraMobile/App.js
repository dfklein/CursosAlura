/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import {
    Platform,
    StyleSheet,
    Text,
    View,
    Image,
    Dimensions,
    ScrollView,
    FlatList
} from 'react-native';

const instructions = Platform.select({
    ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
    android:
        'Double tap R on your keyboard to reload,\n' +
        'Shake or press menu button for dev menu',
});

const width = Dimensions.get('screen').width;


type Props = {};
export default class App extends Component<Props> {
    
    render() {
        const fotos = [
            { id: 1, usuario: 'rafael' },
            { id: 2, usuario: 'alberto' }, 
            { id: 3, usuario: 'vitor' }
        ];

        return (
            <View>
                <FlatList style={{marginTop: 20}}
                    data={fotos}
                    keyExtractor={item => item.id}
                    renderItem={ ({item}) => 
                        <View>
                        <Text>{item.usuario}</Text>
                        <Image source={require('./resources/img/s2-checked.png')} style={{width:width, height:width}} />
                        </View>
                    }
                />
            </View>

            // ---> O código abaixo foi substituído pelo componente FlatList

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

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
});
