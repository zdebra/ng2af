var React = require('react');

var ErrorMessage = React.createClass({

    render: function () {
        return(
            <div className={this.props.className}>
                {this.props.message}
            </div>
        );
    }

});

module.exports = ErrorMessage;