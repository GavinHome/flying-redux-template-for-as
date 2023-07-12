import 'package:flying_redux/flying_redux.dart';

import 'action.dart';
import 'state.dart';

buildReducer() {
  return asReducer(
    <Object, Reducer<$nameState>>{
      $nameAction.someAction: _someAction,
    },
  );
}

$nameState _someAction($nameState state, Action action) {
  final $nameState newState = state.clone();
  return newState;
}
