import RaisedButton from 'material-ui/RaisedButton';
import React from 'react';
import TextField from 'material-ui/TextField';

const registerMessageStyle = {
  fontSize: '36px',
  textAlign: 'center',
  lineHeight: '1.5em',
}

const inputStyle = {
}

const hintStyle = {

}
const formStyle = {
}

const buttonStyle = {
//  margin: '30px',
  height: '60px',
  verticalAlignment: 'center',
  lineHeight: '1em',
}

const RegisterForm = () => (
    <div style={registerMessageStyle}>
      Zapisz się teraz, a jak tylko wystartujemy, to dostaniesz od nas w nim coś fajnego - niespodziankę :)
      <br />
      <form style={formStyle}>
        <TextField hintText="&nbsp;Zapisz się - podaj swój email" hintStyle={hintStyle} inputStyle={inputStyle}/> 
        <br />
        <RaisedButton style={buttonStyle} primary={true}> Zapisuję się :) </RaisedButton>
      </form>
    </div>
);

export default RegisterForm;

