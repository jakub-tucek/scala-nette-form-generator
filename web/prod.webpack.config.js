var webpack = require('webpack');

module.exports = require('./scalajs.webpack.config');

module.exports.plugins = (module.exports.plugins || []).concat([
    new webpack.DefinePlugin({
        'process.env': {
            NODE_ENV: JSON.stringify('production')
        }
    }),
    new webpack.optimize.UglifyJsPlugin()
]);

if (!module.exports.module) {
    module.exports.module = {}
}
module.exports.module.rules = [
    {
        test: /\.css$/,
        use: ['style-loader', 'css-loader']
    }
];
