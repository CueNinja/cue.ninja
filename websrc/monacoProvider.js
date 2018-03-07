let monaco, promise;

export const load = async () => {
  if (!!monaco) {
    return monaco;
  } else {
    if(!promise) {
      promise = new Promise((resolve, reject) => {
        const loaderScript = document.createElement('script');
        loaderScript.type = 'text/javascript';
        loaderScript.src = 'vs/loader.js';
        loaderScript.addEventListener(
          'load',
          () => window.require(['vs/editor/editor.main'],
            () => resolve(monaco = window.monaco))
        );
        document.body.appendChild(loaderScript);
      });
    }
    return promise;
  }
};
