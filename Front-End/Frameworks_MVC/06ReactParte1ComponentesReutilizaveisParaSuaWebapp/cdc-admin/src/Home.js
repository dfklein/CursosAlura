import React, { Component } from 'react';

export default class Home extends Component {
    render() {
        return (
            // Lembre que o JSX (que permite parsear html e xml dentro do javascript) exige 
            // que o retorno seja de um único elemento (um único pai). Portanto você deve
            // envolver sua página em um elemento pai (uma div) no exemplo abaixo para que
            // ele não ache que você está tentando passar dois elementos.
            <div> 
                <div className="header">
                    <h1>Bem-vindo ao sistema</h1>
                </div>
                <div className="content" id="content">
                </div>
            </div>
        );
    }
}
