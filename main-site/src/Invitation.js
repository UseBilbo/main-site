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
  width: '40%',
  position: 'relative',
  left: '45%',
  top: '30%',
  fontWeight: 'bold',
  fontSize: '60px',
  lineHeight: '1.5em',
}

const labelStyle = {
  width: 'auto',
  left: '90%',
  top: '10%',
  position: 'relative',
  backgroundColor: 'rgb(70,70,70)',
  fontSize:'24px',
}

const formStyle = {
  fontSize: '18px',
  color: 'white',
  opacity: 0.4,
  lineHeight: '1em',
  whiteSpace: 'nowrap',
  letterSpacing: 'initial',
  height: '56px',
  padding: '2px',
}

const hintStyle = {
  color: 'white',
  pointerEvents: 'none',
  fontSize: '24px',
  zIndex: 1,
}

const inputStyle = {
  fontWeight: 'normal',
  color: 'white',
  backgroundColor: 'gray',
  fontSize: '24px',
  borderWidth: '2px',
  borderColor: 'white',
  borderStyle: 'solid',
}

const buttonStyle = {
  color: 'white',
  borderColor: 'white',
  borderWidth: '2px',
  borderStyle: 'solid',
  aligh: 'center',
  height: '52px',
}

const Invitation = () => (
  <Paper style={outerPaperStyle}>
    <div style={labelStyle}>w tworzeniu!</div>
    <div style={innerPaperStyle}>
      Prosty sposób na pożyczanie książek od i swoim znajomym
      <form style={formStyle}>
        <TextField hintText="&nbsp;Zapisz się - podaj swój email" hintStyle={hintStyle} inputStyle={inputStyle} fullWidth={true}/> 
        &nbsp;&nbsp;
        <FlatButton icon={<img src="image/keyboard-right.png" width="56px" height="56px" alt="Send"/>} style={buttonStyle}/>
      </form>
    </div>
  </Paper>
);

export default Invitation;