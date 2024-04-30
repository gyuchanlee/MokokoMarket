import { Editor } from '@toast-ui/editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import { useEffect, useRef } from 'react';

const TuiEditor = (props) => {
    const editorRef = useRef(null);
    const toolbarItems = [
        ['heading', 'bold', 'italic', 'strike'],
        ['hr', 'quote', 'ul', 'ol'],
        ['image']
    ];
    let instance = null;

    useEffect(() => {
        if (editorRef.current) {
            instance = new Editor({
                el: editorRef.current,
                initialValue: '여기에 내용을 입력하세요.',
                previewStyle: 'vertical',
                height: '400px',
                initialEditType: 'markdown',
                toolbarItems,
                useCommandShortcut: true,
                events: {
                    change: () => {
                        if (props.onChange) {
                            props.onChange(instance.getMarkdown());
                        }
                    }
                }
            });
        }
    }, [toolbarItems]);

    return (
        <div ref={editorRef}/>
    );
}
export default TuiEditor;