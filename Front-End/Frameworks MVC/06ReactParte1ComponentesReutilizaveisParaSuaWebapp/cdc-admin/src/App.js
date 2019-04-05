import React, { Component } from 'react'; // O framework exige que o módulo principal do React seja exportado com o nome de React.
import './css/pure-min.css'; // lembrando que ./ é a navegação a partir da pasta src.
import './css/side-menu.css';
import AutorBox from './Autor';
// import BotaoSubmitCustomizado from './componentes/BotaoSubmitCustomizado';

class App extends Component {

    // Esta função é o construtor do componente. Ideal para a inicialização do mesmo
    constructor() {
        super();
    }

    // Função chamada após o construtor mas antes da renderização do componente
    componentWillMount() { }

    // Esta função é chamada logo após a renderização do seu componente (após o render())
    componentDidMount() {}

    render() {
        return (
            <div id="layout">

                <a href="#menu" id="menuLink" className="menu-link">

                    <span></span>
                </a>

                <div id="menu">
                    <div className="pure-menu">
                        <a className="pure-menu-heading" href="#">Company</a>

                        <ul className="pure-menu-list">
                            <li className="pure-menu-item"><a href="#" className="pure-menu-link">Home</a></li>
                            <li className="pure-menu-item"><a href="#" className="pure-menu-link">Autor</a></li>
                            <li className="pure-menu-item"><a href="#" className="pure-menu-link">Livro</a></li>


                        </ul>
                    </div>
                </div>

                <div id="main">
                  <div className="header">
                    <h1>Cadastro de Autores</h1>
                  </div>
                  <div className="content" id="content">
                    <AutorBox/>
                  </div>
                </div>  


            </div>
        );
    }
}

export default App;