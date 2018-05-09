var webpack = require('webpack');

module.exports = require('./scalajs.webpack.config');

module.exports.plugins = (module.exports.plugins || []).concat([
    new webpack.DefinePlugin({
        'process.env': {
            NODE_ENV: JSON.stringify('develop')
        }
    })
]);

module.exports.module.rules = [
    {
        test: /\.css$/,
        use: ['style-loader', 'css-loader']
    }
];
