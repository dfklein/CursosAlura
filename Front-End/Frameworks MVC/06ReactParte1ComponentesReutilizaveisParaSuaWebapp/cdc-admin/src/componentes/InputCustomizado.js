import React, { Component } from 'react';

class InputCustomizado extends Component {
    render() {
        // Quando criamos o componente no qual passaremos parâmetros, eles serão recebidos no componente 
        // por meio de um atributo que já vem herdado da classe Component chamado PROPS. O atributo guardará 
        // todos os parâmetros que foram enviados para este componente.
        return(
            <div className="pure-control-group">
              <label htmlFor={this.props.id}>{this.props.label}</label> 
              <input id={this.props.id} type={this.props.type} name={this.props.name} value={this.props.value}  onChange={this.props.onChange}/>                  
            </div> 
        );
    }
}

export default InputCustomizado;