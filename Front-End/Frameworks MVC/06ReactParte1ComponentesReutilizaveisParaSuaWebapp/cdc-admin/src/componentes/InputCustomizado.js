import React, { Component } from 'react';
import PubSub from 'pubsub-js';

class InputCustomizado extends Component {
    constructor(){
        super();
        this.state = {msgErro:''};
    }

    render() {
        // Quando criamos o componente no qual passaremos parâmetros, eles serão recebidos no componente 
        // por meio de um atributo que já vem herdado da classe Component chamado PROPS. O atributo guardará 
        // todos os parâmetros que foram enviados para este componente.
        return(
            <div className="pure-control-group">
              <label htmlFor={this.props.id}>{this.props.label}</label> 
              {/* As propriedades podem ser passadas conforme abaixo */}
              {/* <input id={this.props.id} type={this.props.type} name={this.props.name} value={this.props.value}  onChange={this.props.onChange} onKeyUp={this.props.onKeyUp} />                   */}
              
              {/* Porém você tem a possibilidade de utilizar o spread operator: */}
              <input {...this.props} />                  
              
              {/* Nada te impede de passar novas propriedade> */}
              {/* <input {...this.props} onclick=... />                   */}
              <span className="error">{this.state.msgErro}</span>
            </div> 
        );
    }

    componentDidMount() {
        PubSub.subscribe("erro-validacao",function(topico,erro){            
            if(erro.field === this.props.name){
                this.setState({msgErro:erro.defaultMessage});            
            }
        }.bind(this));

        PubSub.subscribe("limpa-erros",function(topico){                        
            this.setState({msgErro:''});                        
        }.bind(this));        
    }
}

export default InputCustomizado;