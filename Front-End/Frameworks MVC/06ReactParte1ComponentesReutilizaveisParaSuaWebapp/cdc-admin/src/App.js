import React, { Component } from 'react';
import './css/pure-min.css';
import './css/side-menu.css';
import $ from 'jquery';
import InputCustomizado from './componentes/InputCustomizado';
import BotaoSubmitCustomizado from './componentes/BotaoSubmitCustomizado';

class App extends Component {

    // Esta função é o construtor do componente. Ideal para a inicialização do mesmo
    constructor() {
        // Sem a declaração explícita do super() o restante do código apresentará erro de compilação.
        super();
        // a variável state é a que guarda o estado desta classe. O React só reconhece ela com esta
        // função. Ou seja: todas as variáveis que guardam estado da sua view são declarados dentro dele.
        this.state = {
            lista : [],
            nome:'', // estes são os atributos dos inputs. Você os coloca no atributo value e precisa criar um setter que é chamado no onChange do componente.
            email:'',
            senha:''
        };

        // OBS: É considerado má prática fazer chamadas ajax no construtor do seu componente

        // O .bind(this) é uma função que insere a referência this (enquanto classe atual do React)
        // dentro do método declarado (por default, as funções que você declara dentro da sua classe, 
        // não usaram o bound() e não estão associadas com o this do objeto.)
        this.enviaForm = this.enviaForm.bind(this);
        this.setNome = this.setNome.bind(this);
        this.setEmail = this.setEmail.bind(this);
        this.setSenha = this.setSenha.bind(this);

    }

    // Função chamada após o construtor mas antes da renderização do componente
    componentWillMount() { }

    // Esta função é chamada logo após a renderização do seu componente (após o render())
    componentDidMount() {
        $.ajax({
            url: "http://localhost:8080/api/autores",
            dataType: 'json', // tipo do retorno da chamada
            success: function (resposta) {
                // setState é uma função do React em que você solicita a atualização de uma variável
                // do estado desta classe e a atualização na tela.
                this.setState(
                    { 
                        lista: resposta
                    }
                );
            // o .bind é super importante. Ao utilizar a chamada ajax do JQuery você está utilizando
            // o this do JQuery na resposta do callback. Para utilizar o "this" que representa a classe
            // do react dentro do callback você deve fazer o bind.
            }.bind(this)
        }
        );
    }

    enviaForm(evento) {
        // O preventDefault fará com que o evento não atualize toda a árvore do DOM com esta requisição,
        // tornando-o mais performático.
        evento.preventDefault();
        $.ajax({
            url: 'http://localhost:8080/api/autores',
            contentType: 'application/json', // tipo do conteúdo da requisição enviada
            dataType: 'json', // tipo do conteúdo da resposta
            type: 'post', // método http
            data: JSON.stringify( // conteúdo enviado
                {
                    nome: this.state.nome,
                    email: this.state.email,
                    senha: this.state.senha
                }
            ),
            success: function (resposta) {
                this.setState({ lista: resposta });
            }.bind(this),
            error: function (resposta) {
                console.log("erro");
            }
        });
    }

    setNome(evento) {
        this.setState({ nome: evento.target.value });
    }

    setEmail(evento) {
        this.setState({ email: evento.target.value });
    }

    setSenha(evento) {
        this.setState({ senha: evento.target.value });
    }

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
                        <div className="pure-form pure-form-aligned" onSubmit={this.enviaForm.bind(this)} method="post">
                            <form className="pure-form pure-form-aligned">
                                <InputCustomizado id="nome" type="text" name="nome" value={this.state.nome} onChange={this.setNome} label="Nome"/>                                              
                                <InputCustomizado id="email" type="email" name="email" value={this.state.email} onChange={this.setEmail} label="Email"/>                                              
                                <InputCustomizado id="senha" type="password" name="senha" value={this.state.senha} onChange={this.setSenha} label="Senha"/>                                                                      
                                
                                <BotaoSubmitCustomizado label="Gravar"/>
                            </form>

                        </div>
                        <div>
                            <table className="pure-table">
                                <thead>
                                    <tr>
                                        <th>Nome</th>
                                        <th>email</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        this.state.lista.map(function (autor) {
                                            return (
                                                <tr key={autor.id}>
                                                    <td>{autor.nome}</td>
                                                    <td>{autor.email}</td>
                                                </tr>
                                            );
                                        })
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>


            </div>
        );
    }
}

export default App;