import React, { Component } from 'react';
import $ from 'jquery';
import InputCustomizado from './componentes/InputCustomizado';
import PubSub from 'pubsub-js';
import TratadorErros from './TratadorErros';

class FormularioAutor extends Component {

    constructor() {
        // Sem a declaração explícita do super() o restante do código apresentará erro de compilação.
        super();
        // a variável state é a que guarda o estado desta classe. O React só reconhece ela com esta
        // função. Ou seja: todas as variáveis que guardam estado da sua view são declarados dentro dele.
        this.state = {
            lista: [],
            nome: '', // estes são os atributos dos inputs. Você os coloca no atributo value e precisa criar um setter que é chamado no onChange do componente.
            email: '',
            senha: ''
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
                { nome: this.state.nome, email: this.state.email, senha: this.state.senha }
            ),
            success: function (novaListagem) {
                // O PubSub é uma biblioteca de disparo/escuta de eventos. Aqui você está disparando
                // um evento de nome 'atualiza-lista-autores' que carrega um objeto que será passado
                // como parâmetro para os métodos que tiverem se registrado como listeners deste
                // evento (ver componentDidMount() da classe AutorBox).
                PubSub.publish('atualiza-lista-autores', novaListagem);
                this.setState({ nome: '', email: '', senha: '' });
            }.bind(this),
            error: function (resposta) {
                if (resposta.status === 400) {
                    new TratadorErros().publicaErros(resposta.responseJSON);
                }
            },
            beforeSend: function () {
                PubSub.publish("limpa-erros", {});
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
            
            <div className="pure-form pure-form-aligned">
                <form className="pure-form pure-form-aligned" onSubmit={this.enviaForm} method="post">
                    <InputCustomizado id="nome" type="text" name="nome" value={this.state.nome} onChange={this.setNome} label="Nome" />
                    {/*  
                        Quando você está utilizando um componente dentro de outro (um filho dentro de um pai) o filho
                        herdará uma variável chamada props (this.props) que conterá todos os atributos passados aqui
                        Exemplo: this.props.id, this.props.onChange, etc...
                    */}
                    <InputCustomizado id="email" type="email" name="email" value={this.state.email} onChange={this.setEmail} label="Email" />
                    <InputCustomizado id="senha" type="password" name="senha" value={this.state.senha} onChange={this.setSenha} label="Senha" />
                    <div className="pure-control-group">
                        <label></label>
                        <button type="submit" className="pure-button pure-button-primary">Gravar</button>
                    </div>
                </form>

            </div>

        );
    }
}

class TabelaAutores extends Component {

    render() {
        return (
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
                            this.props.lista.map(function (autor) {
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
        );
    }
}

export default class AutorBox extends Component {

    constructor() {
        super();
        this.state = { lista: [] };
    }

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

        // O PubSub é uma biblioteca de disparo/escuta de eventos. O método subscribe aqui
        // regristra a escuta para um evento de nome atualiza-lista-autores e executa uma
        // função quando o mesmo é ouvido. A função recebe como segundo argumento um objeto
        // que foi atachado a quem disparou o evento (que no nosso exemplo é a lista de autores)
        PubSub.subscribe('atualiza-lista-autores', function (topico, novaLista) {
            this.setState({ lista: novaLista });
        }.bind(this));
    }


    render() {
        return (
            <div>
                <div className="header">
                    <h1>Cadastro de autores</h1>
                </div>
                <div className="content" id="content">
                    <FormularioAutor />
                    <TabelaAutores lista={this.state.lista} />
                </div>
            </div>
        );
    }
}