import RaisedButton from 'material-ui/RaisedButton';
import React from 'react';
import TextField from 'material-ui/TextField';
import muiThemeable from 'material-ui/styles/muiThemeable';

const registerMessageStyle = {
  fontSize: '36px',
  textAlign: 'center',
  lineHeight: '1.5em',
  align: 'center',
  padding: '50px',
}

const hintStyle = {
  fontSize: '24px',
}

const formStyle = {
  width: '50%',
  margin: 'auto',
  align: 'center',
  textAlign: 'center',
  verticalAlignment:'middle',
}

const buttonStyle = {
  margin: 30,
  textAlign: 'center',
  borderRadius: '10px',
  height: '60px',
  fontSize: '36px',
}

const labelStyle = {
  fontSize: '36px',
  margin: 30,
}

const RegisterForm = (props) => (
    <div>
      <div style={registerMessageStyle}>
        Zapisz się teraz, a jak tylko wystartujemy, to dostaniesz od nas w nim coś fajnego - niespodziankę :)
        <br />
      </div>
      <form style={formStyle}>
        <TextField hintText="&nbsp;Zapisz się - podaj swój email" name="email" type="email" hintStyle={hintStyle} fullWidth={true}/> 
        <br />
        <button style={{
            zIndex: 1,
            backgroundColor: props.muiTheme.raisedButton.primary,
            border: 'none',
            borderRadius: '10px',
            color: 'white',
            padding: '32px',
            textAlign: 'center',
            textDecoration: 'none',
            display: 'inline-block',
            transition: 'all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms',
            fontSize: '36px',
          }}>Zapisuję się :)</button>
      </form>
      <br />
      <br />
    </div>
);

export default muiThemeable()(RegisterForm);

