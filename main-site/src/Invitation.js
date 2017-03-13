import FlatButton from 'material-ui/FlatButton';
import Paper from 'material-ui/Paper';
import React from 'react';
import TextField from 'material-ui/TextField';

const outerPaperStyle = {
  backgroundImage: 'url(image/photo-man-with-book.jpeg)',
  backgroundSize: 'cover',
  color: 'white',
  height: 650,
  overflowY: 'auto',
  overflowX: 'hidden',
}

const innerPaperStyle = {
  width: '35%',
  position: 'relative',
  left: '45%',
  top: '20%',
  fontWeight: 'bold',
  fontSize: '60px',
  lineHeight: '1.5em',
}

const labelStyle = {
  width: 'auto',
  left: '90%',
  top: '10%',
  position: 'relative',
  backgroundColor: '#6e323d',
  borderRadius: '4px',
  fontSize:'24px',
  fontWeight: '500',
  lineHeight: '25px',
  verticalAlignment: 'middle',
  padding: '10px 20px 10px 20px',
}

const formStyle = {
  fontSize: '18px',
  color: '#7ed9e3',
  whiteSpace: 'nowrap',
  letterSpacing: 'initial',
  lineHeight: '60px',
  padding: '2px',
  width: '80%',
  backgound: 'rgba(255,255,255, 1)',
}

const hintStyle = {
  color: '#7ed9e3',
  pointerEvents: 'none',
  fontSize: '20px',
  fontWeight:'500',
  zIndex: 1,
}

const inputStyle = {
  fontWeight: 'normal',
  color: '#7ed9e3',
//  backgound: 'rgba(161,180, 182, 0.5)',
  fontSize: '24px',
  borderWidth: '2px',
  borderColor: '#7ed9e3',
  borderStyle: 'solid',
}

const buttonStyle = {
  color: '#7ed9e3',
  borderColor: '#7ed9e3',
  borderWidth: '2px',
  borderStyle: 'solid',
  aligh: 'center',
  height: '52px',
  padding: 0,
  margin: 0,
}

const spacerStyle = {
  lineHeight: '45px',
}

const spacer2Style = {
  lineHeight: '45px',
}

const underlineStyle = {
  visibility: 'hidden',
}

const Invitation = () => (
  <Paper style={outerPaperStyle}>
    <div style={labelStyle}>w tworzeniu!</div>
      <div style={innerPaperStyle}>
        Prosty sposób na pożyczanie książek od i swoim znajomym
        <div style={spacerStyle}>&nbsp;</div>
        <form style={formStyle}>
          <TextField hintText="&nbsp;&nbsp;&nbsp;Zapisz się - podaj swój email" 
            name="email" type="email" hintStyle={hintStyle} inputStyle={inputStyle} fullWidth={true}
            underlineStyle={underlineStyle}/> 
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <FlatButton icon={<img src="image/keyboard-right.png" width="56px" height="56px" alt="Send"/>} style={buttonStyle}/>
        </form>
        <div style={spacer2Style}>&nbsp;</div>
      </div>
  </Paper>
);

export default Invitation;