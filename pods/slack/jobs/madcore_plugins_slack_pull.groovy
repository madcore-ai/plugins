job('madcore.plugin.slack.pull') {
    wrappers { preBuildCleanup() }
    parameters {
        stringParam('APP_NAME', 'slack', '')
        stringParam('CHANNELS', 'tests', 'Comma separated list of slack channels')
        stringParam('S3_BUCKET', '', 'S3 bucket name')
        booleanParam('ALL_CHANNELS', false, 'Check if you want to ignore channels and get all of them')
    }
    steps {
        def command = """#!/bin/bash
echo "CHANNELS: '\$CHANNELS'"
echo "S3_BUCKET: '\$S3_BUCKET'"
echo "ALL_CHANNELS: '\$ALL_CHANNELS'"

    pushd /opt/madcore/bin
        python run_cmd_in_container.py "\$APP_NAME" "\$APP_NAME-plugin" "madcore_slack -a pull_messages -p '{\\"channels\\": \\"\$CHANNELS\\", \\"s3_bucket\\":\\"\$S3_BUCKET\\", \\"all_channels\\":\$ALL_CHANNELS}'"
    popd
"""
        shell(command)
    }
}
