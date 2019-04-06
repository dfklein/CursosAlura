import React, { Component } from 'react'; // O framework exige que o módulo principal do React seja exportado com o nome de React.
import './css/pure-min.css'; // lembrando que ./ é a navegação a partir da pasta src.
import './css/side-menu.css';
import {Link} from 'react-router'; // é o componente do router que permite a navegação de single page application (sem gerar requisições reais no browser). Ao invés de <a href... você usa <Link href...
// import BotaoSubmitCustomizado from './componentes/BotaoSubmitCustomizado';

class App extends Component {

    // Esta função é o construtor do componente. Ideal para a inicialização do mesmo (comentado para remover o warning de que não está sendo usado)
    // constructor() {
    //     super();
    // }

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
                        <Link className="pure-menu-heading" to="#">Company</Link>

                        <ul className="pure-menu-list">
                            <li className="pure-menu-item"><Link to="#" className="pure-menu-link">Home</Link></li>
                            <li className="pure-menu-item"><Link to="/autor" className="pure-menu-link">Autor</Link></li>
                            <li className="pure-menu-item"><Link to="/livro" className="pure-menu-link">Livro</Link></li>


                        </ul>
                    </div>
                </div>

                <div id="main">
                    {
                        // Lembre-se de que o props é uma variável que herda atributos do componente
                        // pai (neste caso aqui o index.js). O atributo children contém a classe que será
                        // renderizada de acordo com as regras do router no index.js
                        this.props.children
                    }
                </div>  


            </div>
        );
    }
}

export default App;