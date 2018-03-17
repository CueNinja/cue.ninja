import React from 'react';
import { render } from 'react-dom';
import MonacoEditor from './monaco';

class App extends React.Component {
 constructor(props) {
   super(props);
   const content = window.javafx?.getContent?.()
   this.state = {
     content: content?.length > 0 ? content : '// type your code...'
   };
   window.updateContent = ::this.updateContent
 }
 editorDidMount(editor, monaco) {
   editor.focus();
 }
 onChange(newValue, e) {
   window.javafx?.contentChanged(newValue)
 }
 updateContent(content) {
   this.setState({content})
 }
 render() {
   const code = this.state.code;
   const options = {
     selectOnLineNumbers: true
   };
   return (
     <MonacoEditor
       options={{language:'sql'}}
       onChange={::this.onChange}
       content={this.state?.content}
     />
   );
 }
}


render(
 <App />,
 document.getElementById('root')
);
