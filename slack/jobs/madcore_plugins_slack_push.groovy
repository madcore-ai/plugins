job('madcore.plugin.slack.push') {
    wrappers { preBuildCleanup() }
    parameters {
        stringParam('APP_NAME', 'slack', '')
        stringParam('CHANNEL', 'tests', 'Channel name to post message to.')
        stringParam('MESSAGE', '', 'Message text to post.')
    }
    steps {
        def command = """#!/bin/bash
echo "CHANNEL: '\$CHANNEL'"
echo "MESSAGE: '\$MESSAGE'"

    pushd /opt/madcore/bin
        python run_cmd_in_container.py "\$APP_NAME" "\$APP_NAME-plugin" "madcore_slack -a post_message -p '{\\"channel\\": \\"\$CHANNEL\\", \\"text\\":\\"\$MESSAGE\\"}'"
    popd
"""
        shell(command)
    }
}
