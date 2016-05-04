import React from 'react';
import {update} from 'react'

export default class ErrorMessage extends React.Component {

    render() {
        return(
            <div className="error-message-container">
                {this.props.message}
            </div>
        );
    }



}