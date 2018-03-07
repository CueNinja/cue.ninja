const path = require('path');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const webpack = require('webpack');
const HTMLWebpackPlugin = require('html-webpack-plugin');

process.env.BABEL_ENV = process.env.NODE_ENV || 'development';

const plugins = [
  new webpack.DefinePlugin({
    'process.env': { NODE_ENV: JSON.stringify(process.env.NODE_ENV || 'development') },
  }),
  new CopyWebpackPlugin([
    {
      from: 'node_modules/monaco-editor/min/vs',
      to: 'vs',
    }
  ]),
  new HTMLWebpackPlugin({
    filename: 'index.html',
    template: 'template.html'
  })
];

if(process.env.NODE_ENV !== "production") {
  plugins.push(new webpack.SourceMapDevToolPlugin({
    exclude: /node_modules/,
  }));
} else {
  plugins.push(new webpack.optimize.OccurrenceOrderPlugin());
}

module.exports = {
  entry: ['@babel/polyfill', './index.js'],

  output: {
    path: path.resolve(path.join('..', "src", "main", "resources", "ninja", "cue", "views", "monaco")),
    filename: 'bundle.js'
  },

  plugins: plugins,

  resolve: {
    extensions: ['.js', '.jsx', '.json'],
  },

  module: {
    rules: [
      {
        test: /\.html$/,
        loader: 'html-loader',
      },
      {
        test: /\.json$/,
        loader: 'json',
      },
      {
        test: /\.js$/,
        loader: 'babel-loader'
      }
    ]
  },
  devServer: {
    contentBase: './'
  }
}
