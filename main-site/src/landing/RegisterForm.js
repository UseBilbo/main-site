import React from 'react';
import TextField from 'material-ui/TextField';
import muiThemeable from 'material-ui/styles/muiThemeable';

const registerMessageStyle = {
  fontSize: '36px',
  textAlign: 'center',
  lineHeight: '1.5em',
  align: 'center',
  padding: '50px 150px 50px 150px',
  color: '#6e323d'
}

const formStyle = {
  width: '50%',
  margin: 'auto',
  align: 'center',
  textAlign: 'center',
  verticalAlignment:'middle',
}

const hintStyle = {
  fontSize: '28px',
  color: '#7ed9e3',
  zIndex: '-1',
}

const inputStyle = {
  color: '#7ed9e3',
  fontSize: '28px',
}

const RegisterForm = (props) => (
    <div>
      <div style={registerMessageStyle}>
        Zapisz się teraz, a jak tylko wystartujemy, to dostaniesz od nas w nim coś fajnego - niespodziankę :)
        <br />
      </div>
      <form style={formStyle}>
        <div style={{border:'2px', backgroundColor: 'rgba(126,217,227,0.1)', padding: '20px 50px 30px 50px'}}>
          <br />
          <TextField hintText="jestes.cudowna.osoba@gmail.com" name="email" type="email"
            inputStyle={inputStyle}
            hintStyle={hintStyle} fullWidth={true}
            underlineShow={false}/>
        </div>
        <br />
        <br />
        <button style={{
            zIndex: 1,
            backgroundColor: '#7ed9e3',
            border: 'none',
            borderRadius: '10px',
            color: 'white',
            padding: '25px',
            textAlign: 'center',
            textDecoration: 'none',
            display: 'inline-block',
            transition: 'all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms',
            fontSize: '24px',
            fontWeight: '600',
          }}>Zapisuję się :)</button>
      </form>
      <br />
      <br />
    </div>
);

export default muiThemeable()(RegisterForm);

