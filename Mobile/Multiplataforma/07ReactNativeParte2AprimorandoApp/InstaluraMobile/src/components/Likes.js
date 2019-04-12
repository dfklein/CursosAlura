import React, { Component } from 'react';
import {
    View,
    Text,
    Image,
    TouchableOpacity,
    StyleSheet
} from 'react-native';

export default class Likes extends Component {
    constructor(props) {
        super(props);

    }

     carregaIcone(likeada) {
        return likeada
            ? require('../../resources/img/s2-checked.png')
            : require('../../resources/img/s2.png');
    }


    exibeLikes(likers) {
        if (likers.length <= 0)
            return;

        return (<Text style={styles.likes}>{likers.length} {likers.length > 1 ? 'curtidas' : 'curtida'}</Text>)
    }

    render() {
        const { foto, likeCallback } = this.props;
        return (
            <View>
                {/* O onpress não recebe uma função diretamente, por isso o lambda */}
                <TouchableOpacity onPress={() => {likeCallback(foto.id)}}>
                    <Image source={this.carregaIcone(foto.likeada)}
                        style={styles.botaoDeLike} />
                </TouchableOpacity>
                {this.exibeLikes(foto.likers)}
            </View>
        );
    }

}

const styles = StyleSheet.create({
    botaoDeLike: {
        marginBottom: 10,
        width: 40,
        height: 40
    },

    likes: {
        fontWeight: 'bold'
    }
});