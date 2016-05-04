import React from 'react';
import AuthenticatedComponent from './AuthenticatedComponent'
import {withRouter} from 'react-router';
import BookService from '../services/BookService';
import BookActions from '../actions/BookActions';
import BookStore from '../stores/BookStore';
import TableColumn from './TableColumn';

export default AuthenticatedComponent(class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            books: BookStore.books
        }
    }

    componentDidMount() {
        this.changeListener = this._onChange.bind(this);
        BookStore.addChangeListener(this.changeListener);
        
        BookService.allBooks
            .then((response) => {
                BookActions.getAllBooks(response);
            })
            .catch((err) => {
                console.log(err);
            });
    }

    componentWillUnmount() {
        BookStore.removeChangeListener(this.changeListener);
    }

    _onChange() {
        this.setState({
            books: BookStore.books
        });
    }

    render() {

        var rows = [];
        if(this.state.books.length > 0) {
            rows = this.state.books.map((book)=>{
                return (
                    <tr key={book.id}>
                        <TableColumn content={book.id}/>
                        <TableColumn content={book.added.username}/>
                        <TableColumn content={book.name}/>
                        <TableColumn content={book.description}/>
                        <TableColumn content={book.author.name}/>
                        <TableColumn content={book.dateAdded}/>
                        <TableColumn content={book.isnb}/>
                    </tr>
                );
            });
        }

        return (
            <div>
                <h2>List of all books</h2>
                <table className="pure-table pure-table-horizontal">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Added</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Author</th>
                        <th>Created</th>
                        <th>ISNB</th>
                    </tr>
                    </thead>

                    <tbody>
                    {rows}
                    </tbody>
                </table>
            </div>
        );
    }



});