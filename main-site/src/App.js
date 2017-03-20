import React, { Component } from 'react'
import { cyan500, cyan700, darkBlack, fullBlack, grey100, grey300, grey400, grey500, pinkA200, white } from 'material-ui/styles/colors'

import Comments from './Comments'
import Dialog from 'material-ui/Dialog'
import FlatButton from 'material-ui/FlatButton'
import Invitation from './Invitation'
import LandingFooter from './LandingFooter'
import LandingPageTop from './LandingPageTop'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider'
import RegisterForm from './RegisterForm'
import { fade } from 'material-ui/utils/colorManipulator'
import getMuiTheme from 'material-ui/styles/getMuiTheme'

const muiTheme = getMuiTheme({
  palette: {
    primary1Color: cyan500,
    primary2Color: cyan700,
    primary3Color: grey400,
    accent1Color: pinkA200,
    accent2Color: grey100,
    accent3Color: grey500,
    textColor: darkBlack,
    alternateTextColor: white,
    canvasColor: white,
    borderColor: grey300,
    disabledColor: fade(darkBlack, 0.3),
    pickerHeaderColor: cyan500,
    clockCircleColor: fade(darkBlack, 0.07),
    shadowColor: fullBlack
  }
})

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
    case "1": return "Valid email is required!";
    case "2": return "Provided email alreary registered in the UseBilbo!";
    case "3": return "Error while trying to register email.";
    default: return "Unknown error";
  }
}

const errorText = computeErrorText();

class Landing extends Component {
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
        <LandingFooter />
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

const tyMiddleBoxStyle = {
  width: '100%',
  backgroundColor: '#f0eeed',
}
const tyLeftPicStyle = {
  backgroundImage: 'url(image/sunset-hand-garden-book2.png)',
  backgroundPosition: 'right 0px',
  backgroundRepeat: 'no-repeat',
  backgroundSize: '100% auto',
  height: '650px',
  textAlign: 'center',
  fontSize:'44px',
  lineHeight: '1.5em',
  color: '#454545',
  verticalAlign: 'top',
}
const tyRightPanelStyle = {
  width: '50%',
  padding: '40px',
}
const imgStyle = {
  height: '40px',
  margin: 'auto',
  verticalAlign: 'middle'
}

const ThankYouPage = () => (
  <div>
    <LandingPageTop landing={false}/>
      <table style={tyMiddleBoxStyle}>
      <tbody>
        <tr>
          <td style={tyLeftPicStyle}>
            <div style={{margin: '100px 140px 180px 140px'}}>
              CZYTAĆ KAŻDY MOŻE, TROCHĘ LEPIEJ LUB TROCHĘ GORZEJ
            </div>
          </td>
          <td style={tyRightPanelStyle}>
            <br />
            <div style={{textAlign: 'center', fontSize:'24px', lineHeight:'1.5em'}}>
            DAJ ZNAĆ ZNAJOMYM!
            </div>
            <br />
            <div style={{textAlign: 'center', fontSize:'40px', fontWeight: 600, lineHeight:'1.5em', color: '#00114f'}}>
            ZAPROŚ SWOICH
            ZNAJOMYCH DO BILBO
            </div>
            <br />
            <div style={{textAlign: 'center'}}>
            Bilbo to inicjatywa, która działa wtedy, gdy są w niej Twoi znajomi. Jeżeli myślisz, że pomysł Bilbo
            mógłby się im spodobać, nie zachowuj tej wiedzy tylko dla siebie. Podziel się ze znajomymi! :)
            </div>
            <br />
            <br />
            <div style={{width: '100%', backgroundColor: 'white', textAlign: 'center', fontSize: '20px', fontWeight: 500, lineHeight: '2em'}}>
              {`${window.location}`}
            </div>
            <br />
            <br />
            <div style={{textAlign: 'center'}}>
            <span><a href={`http://www.facebook.com/sharer.php?u=${encodeURIComponent(window.location)}`}><img src="image/fb-logo-blue-50.png" alt="Facebook" /></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="image/bar.png" alt="Bar"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href={`http://twitter.com/share?text=Joined%20UseBilbo%20&url=${window.location}`}><img src="image/twitter-logo-blue-50.png" alt="Twitter" /></a></span>
            </div>
            <br />
          </td>
        </tr>
      </tbody>
      </table>
      <div style={{backgroundColor: 'white', color:'#454545', textAlign:'center', margin: '50px'}}>
        <div style={{fontSize: '24px', fontWeight: 500}}>Dołącz też do grupy na facebooku</div>
        <br />
        <br />
        <div style={{fontSize: '18px'}}>Bądź na bieżąco z pracami nad Bilbo i wpływaj na to jak co będzie wyglądało i działało</div>
        <br />
        <br />
        <a href="https://www.facebook.com/groups/400811693627030/" target="_blank"><img style={imgStyle} src='image/fb-logo-blue-50.png' alt='FB Logo'/></a>
      </div>
    <LandingFooter />
  </div>
);

class App extends Component {
  render() {
    var component;
    if (!params.id) {
      component = <Landing />;
    } else {
      component = <ThankYouPage />;
    }

    return (
      <MuiThemeProvider muiTheme={muiTheme}>
        <div>
          {component}
        </div>
      </MuiThemeProvider>
    )
  }
}

export default App
