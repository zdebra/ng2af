import React from 'react';

export default class TableColumn extends React.Component {

    render() {
        return(
            <td>{this.props.content}</td>
        );
    }

}