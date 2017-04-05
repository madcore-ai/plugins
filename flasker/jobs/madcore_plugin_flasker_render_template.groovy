Job('madcore.plugin.flasker.render.template') {
    parameters {
      stringParam('APP_NAME', 'flasker', '')
    }

    steps {
        def command = """#!/bin/bash
    cat /opt/madcore/plugins/flasker/templates/rc.yaml.template | sed -e "s/\${APP_NAME}/$APP_NAME/g" > /opt/madcore/plugins/flasker/kub/rc.yaml
    cat /opt/madcore/plugins/flasker/templates/svc.yaml.template | sed -e "s/\${APP_NAME}/$APP_NAME/g" > /opt/madcore/plugins/flasker/kub/svc.yaml
"""
        shell(command)
    }
}
