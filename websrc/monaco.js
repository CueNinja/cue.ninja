import React from 'react';
import ReactResizeDetector from 'react-resize-detector';

import { load as loadMonaco } from './monacoProvider';

class MonacoEditor extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          width: window.innerWidth,
          height: window.innerHeight
        };
    }
    onResize(width, height) {
      this.setState({width, height});
      this.editor?.render();
    }
    async componentDidMount() {
        const monaco = await loadMonaco()
        if (!!this.props.theme) {
            monaco.editor.defineTheme(this.props.theme.key, this.props.theme);
            monaco.editor.setTheme(this.props.theme.key);
        }
        const editor = monaco.editor.create(
          this.el,
          {
            ...this.props.options,
            value: this.props.content,
            automaticLayout: true
          }
        );
        editor.onDidChangeModelContent((event) => {
          this.props?.onChange?.(editor.getValue(), event)
        });
        this.editor = editor;
    }
    render() {
        this.editor?.setValue(this.props.content)
        return (
            <div style={{height: '100%', overflow: 'hidden'}}>
                <ReactResizeDetector handleWidth handleHeight onResize={::this.onResize}/>
                <div
                    ref={el => this.el = el}
                    style={{
                        height: this.state?.height,
                        width: this.state?.width,
                        overflow: 'hidden'
                    }}
                />
            </div>
        );
    }
}

export default MonacoEditor;
