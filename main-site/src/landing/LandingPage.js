import React, { Component } from 'react'

import Comments from './Comments'
import Dialog from 'material-ui/Dialog'
import FlatButton from 'material-ui/FlatButton'
import Invitation from './Invitation'
import LandingFooter from './LandingFooter'
import LandingPageTop from './LandingPageTop'
import RegisterForm from './RegisterForm'

function getUrlParams(search) {
    let hashes = search.slice(search.indexOf('?') + 1).split('&')
    let params = {}
    hashes.map(hash => {
        let [key, val] = hash.split('=');
        params[key] = decodeURIComponent(val);
        return "";
    })

    return params
}

const params = getUrlParams(window.location.search);
const hasError = typeof(params.err) !== 'undefined';

function computeErrorText() {
  if (!hasError) {
    return "";
  }
  debugger;
  switch(params.err) {
    case "1": return "Wpisz poprawny adres email!";
    case "2": return "Ten adres email jest już zarejestrowany w Bilbo!";
    case "3": return "Problem podczas rejestracji adresu email!";
    default: return "Nieznany problem!";
  }
}

const errorText = computeErrorText();

class LandingPage extends Component {
    state = {
        open: hasError,
      };

      handleOpen = () => {
        this.setState({open: true});
      };

      handleClose = () => {
        this.setState({open: false});
      };

  render() {
    const actions = [
          <FlatButton
            label="Cancel"
            primary={true}
            onTouchTap={this.handleClose}
          />,
          <FlatButton
            label="Discard"
            primary={true}
            onTouchTap={this.handleClose}
          />,
        ];
    return (
      <div>
        <LandingPageTop landing={true}/>
        <Invitation />
        <Comments />
        <RegisterForm />
        <LandingFooter  useIcon={true} />
        <Dialog
              actions={actions}
              modal={false}
              open={this.state.open}
              onRequestClose={this.handleClose}
            >
              {errorText}
            </Dialog>
      </div>
    );
  }
}

export default LandingPage;
