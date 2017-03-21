import LandingFooter from './LandingFooter'
import LandingPageTop from './LandingPageTop'
import React from 'react'

const tyMiddleBoxStyle = {
  width: '100%',
  backgroundColor: '#f0eeed',
}
const tyLeftPicStyle = {
  backgroundImage: 'url(image/sunset-hand-garden-book2.png)',
  backgroundPosition: 'right 0px',
  backgroundRepeat: 'no-repeat',
  backgroundSize: '100% auto',
  height: '600px',
  textAlign: 'center',
  fontSize:'44px',
  lineHeight: '1.5em',
  color: '#454545',
  verticalAlign: 'top',
}
const tyRightPanelStyle = {
  width: '50%',
  padding: '80px',
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
            <div style={{textAlign: 'center', margin: '20px'}}>
            Bilbo to inicjatywa, która działa wtedy, gdy są w niej Twoi znajomi. Jeżeli myślisz, że pomysł Bilbo
            mógłby się im spodobać, nie zachowuj tej wiedzy tylko dla siebie. Podziel się ze znajomymi! :)
            </div>
            <br />
            <div style={{width: '100%', backgroundColor: 'white', textAlign: 'center', fontSize: '20px', fontWeight: 500, lineHeight: '2em'}}>
              {`${window.location}`}
            </div>
            <br />
            <br />
            <div style={{textAlign: 'center'}}>
              <span>
                <a target='_blank' href={`https://www.facebook.com/sharer/sharer.php?u=${encodeURIComponent(window.location)}`}>
                  <img src="image/fb-logo-blue-50.png" alt="Facebook" />
                </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <img src="image/bar.png" alt="Bar"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a target='_blank' href={`https://twitter.com/home?status=Just%20joined%20UseBilbo%20${encodeURIComponent(window.location)}`}>
                  <img src="image/twitter-logo-blue-50.png" alt="Twitter" />
                  </a>
              </span>
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
    <LandingFooter useIcon={false} />
  </div>
);

export default ThankYouPage;
