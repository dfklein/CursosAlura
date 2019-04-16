import React, { Component } from 'react';
import {
    StyleSheet,
    Dimensions,
    FlatList,
    Platform
} from 'react-native';
import { createStackNavigator, createAppContainer } from "react-navigation";
import LoginScreen from './src/screens/LoginScreen'
import FeedScreen from './src/screens/FeedScreen'

// export default class App extends Component {


// }

const RootStack = createStackNavigator({
    Home: LoginScreen,
    Feed: FeedScreen
  },
  {
    initialRouteName: "Home"
  }
);
 
export default createAppContainer(RootStack);
 
const styles = StyleSheet.create({
 
  MainContainer: {
 
    flex: 1,
    justifyContent: 'center',
    backgroundColor : '#f5fcff',
    padding: 11
 
  },
 
  text:
  {
     fontSize: 22,
     color: '#000',
     textAlign: 'center',
     marginBottom: 10
  },
 
});
