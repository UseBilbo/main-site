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
  width: '48%',
  position: 'relative',
  left: '48%',
  top: '20%',
  fontWeight: 'bold',
  fontSize: '50px',
  lineHeight: '1.5em',
}

const labelStyle = {
  width: 'auto',
  left: '85%',
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
  fontWeight:'600',
  zIndex: 1,
}

const inputStyle = {
  fontWeight: 'normal',
  color: '#7ed9e3',
  fontSize: '24px',
}

const spacerStyle = {
  lineHeight: '45px',
}

const spacer2Style = {
  lineHeight: '45px',
}

const buttonStyle = {
  color: '#7ed9e3',
  borderColor: '#7ed9e3',
  borderWidth: '2px',
  borderStyle: 'solid',
  aligh: 'center',
  height: '100px',
  padding: 0,
  margin: 0,
}

const inputBackgroundStyle = {
  border:'2px',
  backgroundColor: 'rgba(255,255,255,0.1)',
  padding: '0px 20px 0px 20px',
  borderWidth: '2px',
  borderColor: '#7ed9e3',
  borderStyle: 'solid',
}

//            onTouchTap={(e) => {console.log("Submitting %o", e);}}
const Invitation = () => (
  <Paper style={outerPaperStyle}>
    <div style={labelStyle}>w tworzeniu!</div>
      <div style={innerPaperStyle}>
        Prosty sposób na pożyczanie książek od i swoim znajomym
        <div style={spacerStyle}>&nbsp;</div>
        <form style={formStyle} id="form1" action="/api/register" method="POST">
          <div style={inputBackgroundStyle}>
          <TextField hintText="&nbsp;&nbsp;&nbsp;Zapisz się - podaj swój email" 
            name="email" type="email" hintStyle={hintStyle} inputStyle={inputStyle} fullWidth={true}
            underlineShow={false}/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <FlatButton
            type="submit"
            icon={<img src="image/keyboard-right.png" alt="Send"/>}
            style={buttonStyle}/>
          </div>
        </form>
        <div style={spacer2Style}>&nbsp;</div>
      </div>
  </Paper>
);

export default Invitation;