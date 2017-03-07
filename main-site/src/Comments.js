import React from 'react';

const containerStyle = {
  backgroundColor: 'white',
  color: 'black',
  width: '100%',
}

const firstTitleStyle = {
  fontSize: '28px',
  verticalAlign: 'top',
  align: 'left',
}

const secondTitleStyle = {
  fontSize: '28px',
  verticalAlign: 'top',
  align: 'right',
}

const firstCommentStyle = {
  fontSize: '24px',
  position: 'relative',
  left: '1%',
}

const secondCommentStyle = {
  // fontSize: '24px',
  // position: 'relative',
  // right: '1%',
}

const leftDividerStyle = {
  display: 'block',
  border: 0,
  borderTop: '3px solid #000',
  margin: '1em 0',
  padding: 0,
  width: '50%',
  align: 'left',
}

const rightDividerStyle = {
  // display: 'block',
  // border: 0,
  // borderTop: '3px solid #000',
  // margin: '1em 0',
  // padding: 0,
  // width: '50%',
  // align: 'right',
}

const firstCommentTextStyle = {
  position: 'relative',
  left: '3%',
  verticalAlign: 'top',
  width: '50%',
}

const secondCommentTextStyle = {
  // position: 'relative',
  // right: '3%',
  // verticalAlign: 'top',
  // width: '50%',
}

const CommentLeft = (props) => (
  <div style={props.style} >
    <hr style={props.dividerStyle}/>
    <img src={props.icon} alt=""/><span style={props.titleStyle}>{props.title}</span>
    <br/>
    <div style={props.textStyle}>
      {props.children}
    </div>
  </div>
);

const CommentRight = (props) => (
  <div style={props.style} >
    <hr style={props.dividerStyle}/>
    <span style={props.titleStyle}>{props.title}</span><img src={props.icon} alt=""/>
    <br/>
    <div style={props.textStyle}>
      {props.children}
    </div>
  </div>
);

const Comments = () => (
  <div style={containerStyle}>
    <CommentLeft style={firstCommentStyle} 
      title="Odbądź podróż po świecie Twoich znajomych"
      titleStyle={firstTitleStyle}
      icon="image/account-search.png"
      dividerStyle={leftDividerStyle}
      textStyle={firstCommentTextStyle}
      >
      Widząc co czytają i jakie książki posiadają, jeszcze lepiej poznasz swoich znajomych i odkryjesz tytuły, które wiesz że chcesz przeczytać.
    </CommentLeft>
    <CommentRight style={secondCommentStyle}
      title="Wzmacniaj więzi ze znajomymi"
      titleStyle={secondTitleStyle}
      icon="image/account-switch.png"
      dividerStyle={rightDividerStyle}
      textStyle={secondCommentTextStyle}
      >
      Lubimy pomagać nawet nieznajomym, ponieważ zawsze wtedy czujemy uczucie wdzięczności, które ma do nas osoba której pomogliśmy.
      Pożyczając książki naszym przyjaciołom, pokazujemy że nam na nich zależy, przez co i oni z radością będą chcieli pomóc i Tobie.
    </CommentRight>
  </div>
);

export default Comments;